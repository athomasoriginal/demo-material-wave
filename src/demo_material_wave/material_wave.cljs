(ns ^:figwheel-hooks demo-material-wave.material-wave
  (:require
    [demo-material-wave.demo-css-transition-sentinel :as demo-css-transition-sentinel]))

(def body (.. js/document -body))
(def demo-wave (.. body (getElementsByClassName "js-css-transition-sentinel")))


(when (array-seq demo-wave)
  (-> (first (array-seq demo-wave))
      (.addEventListener "mousedown" demo-css-transition-sentinel/start-wave! false)))
