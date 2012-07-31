(ns lalea.views.index
  (:require [lalea.views.common :as common]
            [noir.response :as resp]
            [lalea.models.user :as user])
  (:use [noir.core :only [defpage pre-route]]))

(pre-route [:any [":anything" :anything #"^(?!\/login$).*"]] {} (when-not (user/logged-in?) (resp/redirect "/login")))


(defpage "/"
  []
  (common/layout
    [:p "Welcome to lalea from index.clj"]))


(defpage "/test"
  []
  (common/layout
    [:p "Welcome to Lalea's test page!"]))

