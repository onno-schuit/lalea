(ns lalea.views.index
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


(pre-route [:any [":anything" :anything #"^(?!\/login$).*"]] {} 
  (when-not (user/logged-in?) 
      (resp/redirect (str "/login?origin=" (:uri (ring-request))))))



(defpartial new-list []
  [:p
   (label  "drill[name]" "New list:")
   (text-field "drill[name]")
   (submit-button "Add")])


(defpartial drill [item]
  [:tr
    [:td (item :label)]
    [:td (link-to (str "/drill/delete?id=" (item :id)) "Delete")]
    [:td (link-to (str "/drill/edit?id=" (item :id)) "Edit")]
    [:td (link-to (str "/play?id=" (item :id)) "Play!")] ])


(defpartial list-of-drills [items]
  [:table
   (map drill items)])


(defpage "/"
  []
  (common/layout
    [:p "Welcome to lalea from index.clj"]
    (new-list)
    (list-of-drills (drill/load-by-user-id (session/get :user-id)))))



