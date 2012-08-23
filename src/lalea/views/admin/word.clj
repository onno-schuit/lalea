(ns lalea.views.admin.word
  (:require [lalea.views.common :as common]
            [noir.response :as resp]
            [noir.session :as session]
            [lalea.models.user :as user]
            [lalea.models.word :as word]
            [lalea.models.drill :as drill])
  (:use [noir.core :only [defpage pre-route url-for defpartial render]]
        [noir.request]
        [hiccup.page]
        [hiccup.core]
        [hiccup.form]
        [hiccup.element]))


(defpage [:post "/word/create"] {:keys [drill_id] :as new-word}
  (if (and (drill/load-by-id-and-user-id drill_id (session/get :user-id)) (word/save new-word))
    (resp/redirect (str "/drill/edit?id=" drill_id "&user_id=" (session/get :user-id) ))
    (do 
      ;; Replace this with a Flash error message and display original form
      (println "Sorry, something went wrong while saving the word - meaning pair")
      (render "/drill/edit" {
         :user_id (session/get :user-id) 
         :id drill_id 
         :word_id (:id new-word)}))))


(defpage [:get "/word/delete"] {:keys [id drill_id] :as obsolete-word}
  (if (and (word/is-owner? (word/load-by-id id) (session/get :user-id)) (word/destroy id))
    (resp/redirect (str "/drill/edit?id=" drill_id "&user_id=" (session/get :user-id) ))
    (do 
      ;; Replace this with a Flash error message and display original form
      (println "Sorry, something went wrong while deleting the word - meaning pair")
      (resp/redirect (str "/drill/edit?id=" drill_id "&user_id=" (session/get :user-id) )))))
