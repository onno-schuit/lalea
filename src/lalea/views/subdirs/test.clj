(ns lalea.views.subdir.test
  (:require [lalea.views.common :as common])
  (:use [noir.core :only [defpage pre-route url-for defpartial]]
        [hiccup.page]
        [hiccup.core]
        [hiccup.element]))
(defpage "/subdirs/test" []
  (common/layout
    [:p "Welcome to lalea from subdirs/test.clj"]))
