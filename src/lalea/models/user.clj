(ns lalea.models.user
  (require [noir.session :as session]
           [noir.util.crypt :as crypt]))
(load "/lalea/config")

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
  (let [{stored-pass :password} (first (load-by-username username))]
    (if (and stored-pass (crypt/compare password stored-pass))
      (do
        (session/put! :username username)))))
      ;(vali/set-error :username "Invalid username or password"))))
