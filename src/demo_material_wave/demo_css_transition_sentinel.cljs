(ns demo-material-wave.demo-css-transition-sentinel)

(def ^:private transition-duration
  "Duration of wave transition"
  750)


(defn cleanup-wave!
  "End wave transition"
  [evt wave]
  (let [wave-start    (.. wave (getAttribute "data-wave-start"))
        cleanup-delay (->> (js/Number wave-start)
                           (- (js/Date.now))
                           (- 350))]
    (js/setTimeout
      ; end currently active wave
      (fn []
        (js/window.requestAnimationFrame
          (fn []
            ; end wave
            (.. wave -classList (add "end-wave"))))

        ; final cleanup - remove the wave effect sentinel dom node
        (js/setTimeout
          (try
            (fn []
              (.. wave (remove)))
            (catch :default e
              false))
          transition-duration))
      cleanup-delay)))


(defn cleanup-waves!
  [evt]
  (this-as this
    ; @cleanup handle user rapidly clicking button
    (doseq [wave (array-seq (.. this (getElementsByClassName "start-wave")))]
      (cleanup-wave! evt wave))

    ; @performance - remember to cleanup event listeners
    (.. this (removeEventListener "mouseup" cleanup-waves!))))


(defn start-wave!
  "Start wave transition"
  [evt]
  (this-as this
    (let [wave (.. js/document (createElement "div"))]
      (set! (.. wave -className) "js-wave")
      (.. this (appendChild wave))

      ; @performance cache wave calculation to avoid re-calc during end wave phase
      (.. wave (setAttribute "data-wave-start" (js/Date.now)))

      (js/window.requestAnimationFrame
        (fn []
          ; start wave
          (.. wave -classList (add "start-wave"))))

      ; @performance - only add event listener when ripple activated
      (.. this (addEventListener "mouseup" cleanup-waves! false)))))
