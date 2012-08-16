(ns lalea.views.admin.drill
  (:require [lalea.views.common :as common]
            [noir.response :as resp]
            [noir.session :as session]
            [lalea.models.user :as user]
            [lalea.models.word :as word]
            [lalea.models.drill :as drill])
  (:use [noir.core :only [defpage pre-route url-for defpartial]]
        [noir.request]
        [hiccup.page]
        [hiccup.core]
        [hiccup.form]
        [hiccup.element]))


(defpartial new-word-pair [drill-id]
  [:tr
   [:td 
     (hidden-field "user_id" (session/get :user-id))
     (hidden-field "drill_id" drill-id)
     (text-field "label")]
   [:td (text-field "meaning")]
   [:td (submit-button "Add")]])


(defpartial word-pair [pair]
  [:tr
    [:td (pair :label)]
    [:td (pair :meaning)]
    [:td (link-to (str "/word/delete?id=" (pair :id) "&user_id=" (session/get :user-id)) "Delete")]
    [:td (link-to (str "/word/edit?id=" (pair :id)"&user_id=" (session/get :user-id)) "Edit")] ])


(defpartial list-of-words [words drill-id]
  [:table
    [:tr 
      [:th "Word / Phrase"]
      [:th "Meaning"]]
    (new-word-pair drill-id)
    (map word-pair words)])


(defpage [:get "/drill/edit"] {:keys [user_id id]}
  (if (common/check-ownership user_id)
    (common/layout
      [:p (str "Exercise: " ((drill/load-by-id-and-user-id id user_id) :label) )]
      (form-to [:post "/word/create"]
        (list-of-words (word/load-by-drill-id id) id)))))


(defpage [:get "/drill/delete"] {:as obsolete-drill}
  (if (common/check-ownership (obsolete-drill :user_id))
    (do
      ;; sqlkorma's delete always seems to return false, so no sense in checking return value...
      (drill/destroy obsolete-drill)
      (resp/redirect "/"))))


(defpage [:post "/drill/create"] {:as new-drill}
  (if (common/check-ownership (new-drill :user_id))
    (if (drill/save new-drill)
      (resp/redirect "/")
      (do 
        ;; Replace this with a Flash error message and display original form
        (println "Sorry, something went wrong while saving your drill")
        (resp/redirect "/")))))

