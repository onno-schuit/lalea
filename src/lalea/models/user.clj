(ns lalea.models.user
  (require [noir.session :as session]
           [noir.util.crypt :as crypt]))

(load-file "./src/lalea/config.clj")


(defentity user
    (table :users))


(defn load-by-username
  [username]
  (select user
    (where {:username username})))


(defn logged-in?
  []
  (session/get :username))
  

(defn login! [{:keys [username password] :as user}]
  ;; kormasql DBAL always returns a result as a collection, so we need to use 'first'
  (let [{stored-pass :password user-id :id} (first (load-by-username username))]
    (if (and stored-pass (crypt/compare password stored-pass))
      (do
        (session/put! :username username)
        (session/put! :user-id user-id)))))
      ;(vali/set-error :username "Invalid username or password"))))


;; assoc : associate key with a new value and return new ('updated') map
(defn encrypt-password [{password :password :as user}]
  (assoc user :password (crypt/encrypt password)))

;; Example:
; (update user
;   (set-fields {:password (crypt/encrypt "topsecret")})
;   (where {:username [= "onno"]}))

