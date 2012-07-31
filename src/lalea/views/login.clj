(ns lalea.views.login
  (:require [lalea.views.common :as common]
            [noir.response :as resp]
            [noir.session :as session]
            [lalea.models.user :as user])
  (:use [noir.core :only [defpage]]
        [hiccup.form]))

(defpage [:get "/login"] {}
         (common/layout
           [:p "Please login"]
           (form-to [:post "/login"]
               (text-field "username")
               (password-field "password")
               (submit-button "Login"))))


(defpage [:post "/login"] {:as candidate}
  (if (user/login! candidate)
    ;(resp/redirect "/"))
    (println (str "You are now logged in [redirect to original page] as: " (session/get :username)))
    (println "Sorry, your login attempt failed [render login screen with error flash]")))
