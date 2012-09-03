(ns lalea.views.play
  (:require [lalea.views.common :as common]
            [noir.response :as resp]
            [noir.session :as session]
            [lalea.models.user :as user]
            [lalea.models.drill :as drill]
            [lalea.models.word :as word])
  (:use [noir.core :only [defpage pre-route url-for defpartial]]
        [noir.request]
        [hiccup.page]
        [hiccup.core]
        [hiccup.form]
        [hiccup.element]))


(defpartial display-question [word word-ids]
  [:p (:label word)
    (form-to [:post "/play"]
      (hidden-field "id" (:id word)) 
      (hidden-field "word-ids" (clojure.string/join "," word-ids))
      [:div
        (text-field "meaning") 
        (submit-button "Next")])])


(defpartial show [game]
  [:h1 (:label game)])


(defpage [:get "/play"] {:keys [id]}
  (let [a-game (drill/load-game id (session/get :user-id))]
    (common/layout
      (show a-game)
      (display-question (word/load-by-id (first (:words a-game))) (rest (:words a-game)) ) )))


(defpage [:post "/play"] {:keys [id word-ids]}
  (let [ids (map (fn [id] (Integer/parseInt id)) (clojure.string/split word-ids #","))]
    (common/layout
      ;(show a-game)
      (display-question (word/load-by-id (first ids))  (rest ids)))))
      ;(display-question (first word-ids) (rest word-ids))))
