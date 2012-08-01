(ns lalea.views.index
  (:require [lalea.views.common :as common]
            [noir.response :as resp]
            [noir.session :as session]
            [lalea.models.user :as user])
  (:use [noir.core :only [defpage pre-route url-for]]
        [noir.request]
        [hiccup.page]
        [hiccup.core]))

(defn get-referer []
  (:headers (ring-request)) "referer")

(pre-route [:any [":anything" :anything #"^(?!\/login$).*"]] {} 
  (when-not (user/logged-in?) 
      (resp/redirect (str "/login?origin=" (:uri (ring-request))))))
      ;(resp/redirect (url-for login {:origin  (:uri (ring-request)) }))))


(defpage "/"
  []
  (common/layout
    [:p "Welcome to lalea from index.clj"]))


(defpage "/test"
  []
  (common/layout
    [:p "Welcome to Lalea's test page!"]
    [:p "<a href='/logout'>Logout</a>"]))
