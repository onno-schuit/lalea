(ns lalea.views.login
  (:require [lalea.views.common :as common]
            [noir.response :as resp]
            [noir.session :as session]
            [lalea.models.user :as user])
  (:use [noir.core :only [defpage]]
        [noir.request]
        [hiccup.form]))



(defpage [:get "/login"] {:as querystring}
  (common/layout
    [:p (str "Please login to access " (:origin querystring))]
    (form-to [:post "/login"]
      (hidden-field "origin" (:origin querystring))
      (text-field "username")
      (password-field "password")
      (submit-button "Login"))))


(defpage [:post "/login"] {:as candidate}
  (if (user/login! candidate)
    (do 
      (println (str "You are now logged in [redirect to original page: " (:origin candidate) "] as: " (session/get :username)))
      (resp/redirect (:origin candidate))) 
    (do 
      (println "Sorry, your login attempt failed [render login screen with error flash]")
      (resp/redirect "/login") )))


(defpage [:get "/logout"] {}
  (do
    (session/remove! :username)
    (resp/redirect "/")))
