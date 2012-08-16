(ns lalea.views.admin.drill
  (:require [lalea.views.common :as common]
            [noir.response :as resp]
            [noir.session :as session]
            [lalea.models.user :as user]
            [lalea.models.drill :as drill])
  (:use [noir.core :only [defpage pre-route url-for defpartial]]
        [noir.request]
        [hiccup.page]
        [hiccup.core]
        [hiccup.form]
        [hiccup.element]))


(defpartial word-pair [pair]
  [:tr
    [:td (pair :label)]
    [:td (pair :meaning)]
    [:td (link-to (str "/word/delete?id=" (pair :id) "&user_id=" (session/get :user-id)) "Delete")]
    [:td (link-to (str "/word/edit?id=" (pair :id)"&user_id=" (session/get :user-id)) "Edit")] ])


(defpartial list-of-words [words]
  [:table
    [:tr 
      [:th "Word / Phrase"]
      [:th "Meaning"]]
    (map word-pair words)])


(defpage [:get "/drill/edit"] {:as a-drill}
  (if (common/check-ownership (a-drill :user_id))
    (common/layout
      [:p (str "Exercise: " "title-of-exercise")]
      (list-of-words []))))


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
