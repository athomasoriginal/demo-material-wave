(ns demo-material-wave.demo-css-animation-sentinel-one-class)

(def ^:private transition-duration
  "Duration of wave transition"
  ; @note setting this to 750 causes a bit of jank because it is probably being
  ; remove too quickly
  950)


(defn cleanup-wave!
  "End wave transition"
  [evt wave]
  (let [wave-start    (.. wave (getAttribute "data-wave-start"))
        cleanup-delay (->> (js/Number wave-start)
                           (- (js/Date.now))
                           (- 350))]
    (js/setTimeout
      ; end currently active wave
      (try
        (fn []
          ; final cleanup - remove the wave effect sentinel dom node
          (.. wave (remove)))
        (catch :default e
          false))
      ; add the delay + transition-delay to match removal time
      (+ cleanup-delay transition-duration))))


(defn cleanup-waves!
  [evt]
  (this-as this
    ; @cleanup handle user rapidly clicking button
    (doseq [wave (array-seq (.. this (getElementsByClassName "start-wave-end-wave")))]
      (cleanup-wave! evt wave))

    ; @performance - remember to cleanup event listeners
    (.. this (removeEventListener "mouseup" cleanup-waves!))))


(defn start-wave!
  "Start wave animation"
  [evt]
  (this-as this
    (let [wave (.. js/document (createElement "div"))]
      (set! (.. wave -className) "wave")
      (.. this (appendChild wave))

      ; @performance cache wave calculation to avoid re-calc during end wave phase
      (.. wave (setAttribute "data-wave-start" (js/Date.now)))

      (js/window.requestAnimationFrame
        (fn []
          ; start wave
          (.. wave -classList (add "start-wave-end-wave"))))

      ; @performance - only add event listener when ripple activated
      (.. this (addEventListener "mouseup" cleanup-waves! false)))))
