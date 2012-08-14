(ns lalea.server
  (:require [noir.server :as server]))

(server/load-views-ns 'lalea.views)
;(server/load-views "src/lalea/views/subdir/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'lalea})))

