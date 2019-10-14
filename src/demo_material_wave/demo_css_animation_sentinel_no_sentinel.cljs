(ns demo-material-wave.demo-css-animation-sentinel-no-sentinel
  "Instead of adding and removing elements we need to add/remove classes.

  A big thing to remember when working with sentinel element vs. pseudoclasses
  is that you have to add and remove the pseudoclasses like you would the
  sentinel classes.

  difference between sentinel and non sentinel:
    > start-wave - no longer creating/adding elements to the screen
    > cleanup-wave! becomes much simpler
    > no longer need to track the target and the evt
    > no longer need to use `this-as`
    > handling multiple clicks is challenging with this model because it
      takes a little more thinking")


(def ^:private transition-duration
  "Duration of wave transition"
  ; @note setting this to 750 causes a bit of jank because it is probably being
  ; remove too quickly
  950)


(defn cleanup-wave!
  "End wave transition"
  [evt]
  (let [wave-start    (.. evt -target (getAttribute "data-wave-start"))
        cleanup-delay (->> (js/Number wave-start)
                           (- (js/Date.now))
                           (- 350))]
    (js/setTimeout
      ; end currently active wave
      (try
        (fn []
          ; end wave
          (js/window.requestAnimationFrame
            (fn []
              (.. evt -target -classList (remove "wave--no-sentinel" "start-wave-end-wave--no-sentinel")))))
        (catch :default e
          false))
      ; add the delay + transition-delay to match removal time
      (+ cleanup-delay transition-duration))))


(defn start-wave!
  "Start wave transition"
  [evt]
  ; @performance cache wave calculation to avoid re-calc during end wave phase
  (.. (.. evt -target) (setAttribute "data-wave-start" (js/Date.now)))

  (js/window.requestAnimationFrame
    (fn []
      ; start wave
      (.. (.. evt -target) -classList (add "wave--no-sentinel" "start-wave-end-wave--no-sentinel"))))

  ; @performance - only add event listener when ripple activated
  (.. (.. evt -target) (addEventListener "mouseup" cleanup-wave! false)))
