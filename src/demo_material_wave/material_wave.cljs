(ns ^:figwheel-hooks demo-material-wave.material-wave
  (:require
    [demo-material-wave.demo-css-transition-sentinel            :as demo-css-transition-sentinel]
    [demo-material-wave.demo-css-animation-sentinel             :as demo-css-animation-sentinel]
    [demo-material-wave.demo-css-animation-sentinel-one-class   :as demo-css-animation-sentinel-one-class]
    [demo-material-wave.demo-css-animation-sentinel-no-sentinel :as demo-css-animation-sentinel-no-sentinel]))

(def body (.. js/document -body))
(def demo-wave-1 (.. body (getElementsByClassName "js-css-transition-sentinel")))
(def demo-wave-2 (.. body (getElementsByClassName "js-css-animation-sentinel-2-class")))
(def demo-wave-3 (.. body (getElementsByClassName "js-css-animation-sentinel-1-class")))
(def demo-wave-4 (.. body (getElementsByClassName "js-css-animation-sentinel-no-sentinel")))


(when (array-seq demo-wave-1)
  (-> (first (array-seq demo-wave-1))
      (.addEventListener "mousedown" demo-css-transition-sentinel/start-wave! false)))


(when (array-seq demo-wave-2)
  (-> (first (array-seq demo-wave-2))
      (.addEventListener "mousedown" demo-css-animation-sentinel/start-wave! false)))


(when (array-seq demo-wave-3)
  (-> (first (array-seq demo-wave-3))
      (.addEventListener "mousedown" demo-css-animation-sentinel-one-class/start-wave! false)))


(when (array-seq demo-wave-4)
  (-> (first (array-seq demo-wave-4))
      (.addEventListener "mousedown" demo-css-animation-sentinel-no-sentinel/start-wave! false)))
