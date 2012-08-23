(ns lalea.views.play
  (:require [lalea.views.common :as common]
            [noir.response :as resp]
            [noir.session :as session]
            [lalea.models.user :as user]
            [lalea.models.drill :as drill])
  (:use [noir.core :only [defpage pre-route url-for defpartial]]
        [noir.request]
        [hiccup.page]
        [hiccup.core]
        [hiccup.form]
        [hiccup.element]))


(defpartial display-question [word-id]
  [:p "Show word here..."])

(defpage [:get "/play" {:keys [drill_id]}]
  []
  (common/layout
    (display-question 1)))


(defpage [:post "/play"] {:keys [drill_id words]}
  (common/layout
    (display-question 1)))
