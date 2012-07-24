(ns lalea.views.login
  (:require [lalea.views.common :as common])
  (:use [noir.core :only [defpage]]))

(defpage "/login" []
         (common/layout
           [:p "Please login"]))
