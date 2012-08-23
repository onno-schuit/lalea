(ns lalea.views.index
  (:require [lalea.views.common :as common]
            [noir.response :as resp]
            [noir.session :as session]
            [lalea.models.user :as user]
            [lalea.models.drill :as drill]
            [noir.validation :as vali])
  (:use [noir.core :only [defpage pre-route url-for defpartial]]
        [noir.request]
        [hiccup.page]
        [hiccup.core]
        [hiccup.form]
        [hiccup.element]))


(pre-route [:any [":anything" :anything #"^(?!\/login$).*"]] {} 
  (when-not (user/logged-in?) 
      (resp/redirect (str "/login?origin=" (:uri (ring-request))))))



(defpartial new-list [list]
  [:p
    (vali/on-error :label common/error-item)
    (form-to [:post "/drill/save"]
      (when (list :id)
        (hidden-field "id" (:id list)))
      (label  "label" "New list:")
      (hidden-field "user_id" (session/get :user-id))
      (text-field "label" (:label list))
      (submit-button "Add"))])


(defpartial drill [item]
  [:tr
    [:td (item :label)]
    [:td (link-to (str "/drill/delete?id=" (item :id) "&user_id=" (session/get :user-id)) "Delete")]
    [:td (link-to (str "/drill/edit?id=" (item :id)"&user_id=" (session/get :user-id)) "Edit")]
    [:td (link-to (str "/play?id=" (item :id)) "Play!")] ])


(defpartial list-of-drills [items]
  [:table
   (map drill items)])


(defpage "/"
  []
  (common/layout
    [:p "Welcome to lalea from index.clj"]
    (new-list {})
    (list-of-drills (drill/load-by-user-id (session/get :user-id)))))


(defpage [:get "/drill/updatetitle"] {:keys [user_id id]}
(let [a-drill (drill/load-by-id-and-user-id id user_id)]
  (when (and (common/check-identity user_id) (drill/is-owner? user_id (:user_id a-drill)))
    (common/layout
      (new-list a-drill)))))
