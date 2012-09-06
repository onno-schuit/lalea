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
    (form-to [:post "/check-answer"]
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


(defpage [:get "/next-question"] {:keys [id word-ids]}
  (let [ids (map (fn [id] (Integer/parseInt id)) (clojure.string/split word-ids #","))]
    (common/layout
      (display-question (word/load-by-id id)  ids))))


(defpartial correct []
  [:h2 "correct"])


(defpartial incorrect []
  [:h2 "that is not correct..."])


(defpage [:post "/check-answer"] {:keys [id word-ids meaning] :as answer}
  (let [ids (map (fn [id] (Integer/parseInt id)) (clojure.string/split word-ids #","))]
    (if (= meaning (:meaning (word/load-by-id id)))
      (resp/redirect (str "/next-question?id=" (first ids) "&word-ids=" (clojure.string/join "," (rest ids)) ))
      (incorrect))))
