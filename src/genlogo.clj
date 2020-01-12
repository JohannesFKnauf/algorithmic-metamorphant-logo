(ns genlogo
  (:require [dali.io]))

(def golden-ratio
  1.61803398875)

(defn logo [{:keys [dot-radius line-width corner-arc-radius eye-offset]}]
  (let [canvas {:width 1000 :height 1000}
        eye {:x (- (:width canvas) eye-offset)
             :y eye-offset}
        inner-outline-dot {:x (/ (:width canvas) (+ 1 golden-ratio))
                           :y (- (:height canvas) (/ (- (:height canvas) (:y eye)) (+ 1 golden-ratio)))}
        inner-ear-dot {:x (:x inner-outline-dot)
                       :y (- (:y eye) (/ (:y eye) (+ 1 golden-ratio)))}
        line-padding (float (/ line-width 2))
        bottom-right-dot {:x (- (:width canvas) line-padding corner-arc-radius)
                   :y (- (:height canvas) line-padding)}
        bottom-middle-dot {:x (+ (:x inner-outline-dot) (/ (- (:x bottom-right-dot) (:x inner-outline-dot)) (+ 1 golden-ratio)))
                           :y (- (:height canvas) line-padding)}
        ear-end {:x (+ (:x bottom-middle-dot) corner-arc-radius)
                 :y (/ (:height canvas) 2)}
        dot-diameter (* 2 dot-radius)
        view-box-padding-x (- line-padding dot-radius)
        view-box-padding-y view-box-padding-x
        total-width (- (:width canvas) (* 2 view-box-padding-x))
        total-height (- (:height canvas) (* 2 view-box-padding-y))
        line-style {:stroke "#02324b" :path-length 1000 :stroke-width line-width :stroke-linejoin "round" :fill "none" :marker-start "url(#bm)" :marker-end "url(#bm)"}]
      [:dali/page {:width "100%" :height "100%" :view-box (str view-box-padding-x " " view-box-padding-y " " total-width " "total-height)}
       [:defs
        [:circle {:id "bubbel" :cx 0 :cy 0 :r dot-radius :fill "#02324b"}]
        [:marker {:id "bm" :view-box (str "0 0 " dot-diameter " " dot-diameter) :ref-x dot-radius :ref-y dot-radius :marker-units "userSpaceOnUse" :marker-width dot-diameter :marker-height dot-diameter}
         [:use {:x dot-radius :y dot-radius :xlink:href "#bubbel"}]]]
       [:path line-style
        :M [(:x inner-outline-dot)
            (:y inner-outline-dot)]
        :L [(:x inner-outline-dot)
            (- (:height canvas) line-padding)]
        :L [(+ corner-arc-radius line-padding)
            (- (:height canvas) line-padding)]
        :A [corner-arc-radius
            corner-arc-radius]
        0 false true
        [line-padding
         (- (:height canvas) corner-arc-radius line-padding)]
        :L [line-padding
            (+ corner-arc-radius line-padding)]
        :A [corner-arc-radius
            corner-arc-radius]
        0 false true
        [(+ corner-arc-radius line-padding)
         line-padding]
        :L [(- (:width canvas) corner-arc-radius line-padding)
            line-padding]
        :A [corner-arc-radius
            corner-arc-radius]
        0 false true
        [(- (:width canvas) line-padding)
         (+ corner-arc-radius line-padding)]
        :L [(- (:width canvas) line-padding)
            (- (:height canvas) corner-arc-radius line-padding)]
        :A [corner-arc-radius
            corner-arc-radius]
        0 false true
        [(:x bottom-right-dot)
         (:y bottom-right-dot)]]
       [:path line-style
        :M [(:x inner-ear-dot)
            (:y inner-ear-dot)]
        :L [(:x inner-ear-dot)
            (- (/ (:height canvas) 2) corner-arc-radius)]
        :A [corner-arc-radius
            corner-arc-radius]
        0 false false
        [(+ (:x inner-ear-dot) corner-arc-radius)
         (/ (:height canvas) 2)]
        :L [(:x ear-end)
            (:y ear-end)]
        :L [(:x ear-end)
            (- (:height canvas) corner-arc-radius line-padding)]
        :A [corner-arc-radius
            corner-arc-radius]
        0 false true
        [(- (:x ear-end) corner-arc-radius)
         (- (:height canvas) line-padding)]]
       [:use {:x (:x eye) :y (:y eye) :xlink:href "#bubbel"}]
       ;; [:rect {:x 0 :y 0 :width (:width canvas) :height (:height canvas) :fill "none" :stroke "blue" :stroke-width "4"}]  ; frame
       ;; [:line {:x1 (:x inner-outline-dot) :y1 0 :x2 (:x inner-outline-dot) :y2 (:height canvas) :stroke "blue" :stroke-width "4"}]  ; vertical: left inner dot positions
       ;; [:line {:x1 (:width canvas) :y1 0 :x2 0 :y2 (:height canvas) :stroke "blue" :stroke-width "4"}]   ; diagonal: eye positions
       ;; [:line {:x1 0 :y1 (:y eye) :x2 (:width canvas) :y2 (:y eye) :stroke "blue" :stroke-width "4"}]    ; horizontal: eye positions
       ;; [:line {:x1 0 :y1 (:y inner-outline-dot) :x2 (:width canvas) :y2 (:y inner-outline-dot) :stroke "blue" :stroke-width "4"}]    ; horizontal: inner outline dot positions
       ;; [:line {:x1 0 :y1 (:y inner-ear-dot) :x2 (:width canvas) :y2 (:y inner-ear-dot) :stroke "blue" :stroke-width "4"}]    ; horizontal: inner ear dot positions
       ;; [:line {:x1 (:x bottom-right-dot) :y1 0 :x2 (:x bottom-right-dot) :y2 (:height canvas) :stroke "blue" :stroke-width "4"}]  ; vertical: right dot position
       ;; [:line {:x1 (:x bottom-middle-dot) :y1 0 :x2 (:x bottom-middle-dot) :y2 (:height canvas) :stroke "blue" :stroke-width "4"}]  ; vertical: middle dot position
       ;; [:line {:x1 0 :y1 (:y ear-end) :x2 (:width canvas) :y2 (:y ear-end) :stroke "blue" :stroke-width "4"}]  ; horizontal: ear end line positions
       ]))

(defn -main []
  (doseq [dot-radius [40 55 70]
          line-width [50 64 80]
          corner-arc-radius [100 160 220]
          eye-offset [230 265 300]]
    (dali.io/render-svg (logo {:dot-radius dot-radius
                               :line-width line-width
                               :corner-arc-radius corner-arc-radius
                               :eye-offset eye-offset})
                        (str "metamorphant" "-" dot-radius "-" line-width "-" corner-arc-radius "-" eye-offset ".svg"))))
