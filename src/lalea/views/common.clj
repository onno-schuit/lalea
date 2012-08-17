(ns lalea.views.common
  (:require [noir.session :as session])
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css html5]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "lalea"]
               (include-css "/css/reset.css")]
              [:body
               [:h1 "Lalea Rocks!"]
               [:div#wrapper
                content]]))


;; Please note: this is ONLY secure if you always use the user_id in a WHERE clause (not just the record's id)
;; Also, the assumption here is that the session's user-id cannot be compromised...
(defn check-identity [untrusted-user-id]
  (= (str untrusted-user-id) (str (session/get :user-id)) ))
