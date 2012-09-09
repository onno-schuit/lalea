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


(defn get-errors []
  (session/get :errors))


(defn set-errors-to-empty []
  (session/put! :errors []))


(defn add-error [current-word]
  (session/put! :errors (conj (session/get :errors) (:id current-word))))


(defpartial display-question [word word-ids game-id]
  [:p (:label word)
    (form-to [:post "/check-answer"]
      (hidden-field "id" (:id word)) 
      (hidden-field "game-id" game-id) 
      (hidden-field "word-ids" (clojure.string/join "," word-ids))
      [:div
        (text-field "meaning") 
        (submit-button "Next")])])


(defpartial show [game]
  [:h1 (:label game)])


(defpage [:get "/play"] {:keys [id]}
  (do
    (set-errors-to-empty))
  (let [a-game (drill/load-game id (session/get :user-id))]
    (resp/redirect (str "/play-round?game-id=" id "&word-ids=" (clojure.string/join "," (:words a-game)) ))))


(defpartial results []
  [:h1 "Results!"])


(defn map-ids-string-to-vector [word-ids]
  (if (= word-ids "")
    []
    (map (fn [id] (Integer/parseInt id)) (clojure.string/split word-ids #","))))


(defpage [:get "/play-round"] {:keys [game-id word-ids]}
  (common/layout
    (show (drill/load-game game-id (session/get :user-id)))
    (if (not (= "" word-ids))
      (let [ids (map-ids-string-to-vector word-ids)]
        (display-question (word/load-by-id (first ids))  (rest ids) game-id))
      (results))))


(defpartial correction [word]
  [:h2 "Correct is:"]
  [:div (:label word)]
  [:div (:meaning word)])


(defn max-errors? [current-word errors]
  (<= 3 ((frequencies errors) (:id current-word))))


(defn retry [current-word]
  (println "Implement me!"))


(defn handle-wrong-answer [current-word]
  ;; add id to session object of errors (same id can be stored multiple times to compute frequencies)
  ;; if frequency == 3, show correction, otherwise allow another attempt
  (do
    (println (add-error current-word))
    (println (get-errors)))
  (if (max-errors? current-word (get-errors))
    (correction current-word)
    (retry current-word)))



(defpage [:post "/check-answer"] {:keys [id word-ids meaning game-id] :as answer}
  (let [current-word (word/load-by-id id)]
    (if (= meaning (:meaning current-word))
      (resp/redirect (str "/play-round?word-ids=" word-ids "&game-id=" game-id))
      (handle-wrong-answer current-word))))
