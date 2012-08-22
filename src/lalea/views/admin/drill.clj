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


(defpartial edit-word-pair [{:keys [id drill_id label meaning]}] 
  [:td 
    (hidden-field "id" id)
    (hidden-field "user_id" (session/get :user-id))
    (hidden-field "drill_id" drill_id)
    (text-field "label" label)]
  [:td (text-field "meaning" meaning)]
  [:td (submit-button "Add")])


(defpartial list-word-pair [pair]
   [:td (pair :label)]
   [:td (pair :meaning)]
   [:td (link-to (str "/word/delete?id=" (pair :id) "&user_id=" (session/get :user-id) "&drill_id=" (pair :drill_id)) "Delete")]
   [:td (link-to (str "/drill/edit?word_id=" (pair :id) "&user_id=" (session/get :user-id) "&id=" (pair :drill_id)) "Edit")])

(defpartial word-pair [word-id pair]
    [:tr
      (if (= (str word-id) (str (:id pair)))
        (edit-word-pair pair)
        (list-word-pair pair))])


(defpartial list-of-words [words drill-id word-id]
  [:table
    [:tr 
      [:th "Word / Phrase"]
      [:th "Meaning"]]
    (when (nil? word-id) (new-word-pair drill-id))
    (map (partial word-pair word-id) words)])


(defpage [:get "/drill/edit"] {:keys [user_id id word_id]}
  (let [a-drill (drill/load-by-id-and-user-id id user_id)]
    ;(when (and (common/check-identity user_id) (not (nil? a-drill))) ;; -- too implementation dependent 
    ;                                                                       (load-by-id-and-user-id may not return nil but may
    ;                                                                        still have 'failed')
    (when (and (common/check-identity user_id) (drill/is-owner? user_id (:user_id a-drill)))
      (common/layout
        [:p 
         (str "Exercise: " (a-drill :label) " " ) 
         (link-to (str "/drill/updatetitle?user_id=" user_id "&id=" id) "edit title" )]
        (form-to [:post "/word/create"]
          (list-of-words (word/load-by-drill-id id) id word_id))))))


(defpage [:get "/drill/delete"] {:as obsolete-drill}
  (if (common/check-identity (obsolete-drill :user_id))
    (do
      ;; sqlkorma's delete always seems to return false, so no sense in checking return value...
      (drill/destroy obsolete-drill)
      (resp/redirect "/"))))


(defpage [:post "/drill/save"] {:as new-drill}
  ;(common/is-owner? (word/load-by-id id) (session/get :user-id))
  (if (common/check-identity (new-drill :user_id))
    (let [drill-id (drill/save new-drill)]
      (if (number? drill-id)
        (resp/redirect str("/drill/edit?user_id=" (:new-drill :user_id) "&id=" drill-id))
        (do 
          ;; Replace this with a Flash error message and display original form
          (println "Sorry, something went wrong while saving your drill")
          (resp/redirect "/"))))))
