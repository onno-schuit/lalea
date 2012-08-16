(ns lalea.views.admin.word
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

(defpage [:post "/word/create"] {:as word}
  (if (word/save word)
    (resp/redirect (str "/drill/edit?id=" (word :drill_id) "&user_id=" (session/get :user-id) ))
    (do 
      ;; Replace this with a Flash error message and display original form
      (println "Sorry, something went wrong while saving the word - meaning pair")
      (resp/redirect (str "/drill/edit?id=" (word :drill_id) "&user_id=" (session/get :user-id) )))))
