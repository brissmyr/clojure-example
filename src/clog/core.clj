(ns clog.core
  (:use ring.adapter.jetty
        ring.middleware.resource
        ring.middleware.cookies
        ring.middleware.params
        ring.util.response
        clog.templates
        net.cgrand.moustache))

(defn wrap-userbin [app]
  (fn [req]
    (println (:value ((:cookies req) "_ubt")))
    (app req)))

(defn index
  "Index page handler"
  [req]
  (->> (home-page) response))

;;; A simple handler to show send some response to the client.
(def routes
  (app
    (wrap-cookies)
    (wrap-params)
    (wrap-userbin)
    [""] (delegate index)))

(defn -main []
  (run-jetty #'routes {:port 8888 :join? false}))
