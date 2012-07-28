(ns lalea.views.login
  (:require [lalea.views.common :as common]
            [noir.response :as resp])
  (:use [noir.core :only [defpage]]
        [hiccup.form]))

(defpage "/login" []
         (common/layout
           [:p "Please login"]
           (form-to [:post "/login"]
               (text-field "username")
               (password-field "password")
               (submit-button "Login"))))


(defpage [:post "/authenticate"] {:as user}
  (if (user/login! user)
    ;(resp/redirect "/"))
    (println "You are now logged in [redirect to original page]")
    (println "Sorry, your login attempt failed [render login screen with error flash]")))
