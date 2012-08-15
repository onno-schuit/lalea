(ns lalea.views.admin.drill
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


(defpage "/drill/edit"
  []
  (common/layout
    [:p "Welcome to lalea from drill/edit (admin/drill.clj)"]))


(defpage "/drill/delete"
  []
  (common/layout
    [:p "Welcome to lalea from drill/delete (admin/drill.clj)"]))
