(ns lalea.views.login
  (:require [lalea.views.common :as common]
            [noir.response :as resp]
            [noir.session :as session]
            [lalea.models.user :as user])
  (:use [noir.core :only [defpage]]
        [noir.request]
        [hiccup.form]))


(defpage [:get "/login"] {}
         (common/layout
           [:p "Please login"]
           (form-to [:post "/login"]
               (text-field "username")
               (password-field "password")
               (submit-button "Login"))))


(defn get-referer []
    ((:headers (ring-request)) "referer"))


(defpage [:post "/login"] {:as candidate}
  (if (user/login! candidate)
    ;(resp/redirect "/"))
    (do 
      (println (str "You are now logged in [redirect to original page: " (get-referer) "] as: " (session/get :username)))
      (resp/redirect "/")) 
    (do 
      (println "Sorry, your login attempt failed [render login screen with error flash]")
      (resp/redirect "/login") )))


(defpage [:get "/logout"] {}
  (do
    (session/remove! :username)
    (resp/redirect "/")))
