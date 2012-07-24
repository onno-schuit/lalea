(ns lalea.views.index
  (:require [lalea.views.common :as common]
            [noir.response :as resp])
  (:use [noir.core :only [defpage pre-route]]))

(pre-route [:any [":anything" :anything #"^(?!\/login$).*"]] {} (when-not (= 1 2) (resp/redirect "/login")))


(defpage "/"
  []
  (common/layout
    [:p "Welcome to lalea from index.clj"]))

