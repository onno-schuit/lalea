(ns lalea.views.common
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
