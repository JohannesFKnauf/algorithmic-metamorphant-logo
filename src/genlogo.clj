(ns genlogo
  (:require [dali.io]))

(def golden-ratio
  1.61803398875)

(defn logo [{:keys [dot-radius
                    line-width
                    corner-arc-radius
                    eye-offset] :or
             {dot-radius 55
              line-width 64
              corner-arc-radius 160
              eye-offset 265}}]
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
        line-style {:stroke "#02324b" :path-length 1000 :stroke-width line-width :stroke-linejoin "round" :stroke-linecap "round" :fill "none" :marker-start "none" :marker-end "none"}]
      [:dali/page {:width "100%" :height "100%" :view-box (str view-box-padding-x " " view-box-padding-y " " total-width " "total-height)}
       [:defs
        [:circle {:id "bubbel" :cx 0 :cy 0 :r dot-radius :fill "#02324b"}]]
       [:path (merge line-style {:stroke-dasharray 1000 :stroke-dashoffset 1000 :id "hulk"})
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
       [:path (merge line-style {:stroke-dasharray 1000 :stroke-dashoffset 1000 :id "ear"})
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
       [:use {:id "eye"
              :opacity "0.0"
              :transform (str "translate(" (:x eye) " " (:y eye) ")")
              :xlink:href "#bubbel"}]
       [:use {:id "hulk-start" :opacity 0.0 :x 0 :y 0 :xlink:href "#bubbel"}]
       [:use {:id "hulk-end" :opacity 0.0 :x 0 :y 0 :xlink:href "#bubbel"}]
       [:use {:id "ear-start" :opacity 0.0 :x 0 :y 0 :xlink:href "#bubbel"}]
       [:use {:id "ear-end" :opacity 0.0 :x 0 :y 0 :xlink:href "#bubbel"}]

       ; pre-revealhulk
       [:set {:xlink:href "#hulk-start" :attribute-name "opacity" :to "1.0" :begin "0s;hideeye.end+0.1s" :fill "freeze"}]
       [:set {:xlink:href "#hulk-end" :attribute-name "opacity" :to "1.0" :begin "0s;hideeye.end+0.1s" :fill "freeze"}]

       ; revealhulk
       [:animate {:id "revealhulk" :xlink:href "#hulk" :attribute-name "stroke-dashoffset" :from "1000" :to "2000" :begin "0s;hideeye.end+0.1s" :dur "1s" :fill "freeze"}]

       ; revealhulk dots
       [:animateMotion {:xlink:href "#hulk-start" :begin "0s;hideeye.end+0.1s" :dur "1s" :fill "freeze" :keyTimes "0 ; 1" :keyPoints "1 ; 0"}
        [:mpath {:xlink:href "#hulk"}]]
       [:animateMotion {:xlink:href "#hulk-end" :begin "0s;hideeye.end+0.1s" :dur "1s" :fill "freeze" :keyTimes "0 ; 1" :keyPoints "1 ; 1"}
        [:mpath {:xlink:href "#hulk"}]]

       ; pre-revealear
       [:set {:xlink:href "#ear-start" :attribute-name "opacity" :to "1.0" :begin "revealhulk.end" :fill "freeze"}]
       [:set {:xlink:href "#ear-end" :attribute-name "opacity" :to "1.0" :begin "revealhulk.end" :fill "freeze"}]

       ; revealear
       [:animate {:id "revealear" :xlink:href "#ear" :attribute-name "stroke-dashoffset" :from "1000" :to "2000" :begin "revealhulk.end" :dur "1s" :fill "freeze"}]

       ; revealear dots
       [:animateMotion {:xlink:href "#ear-start" :begin "revealhulk.end" :dur "1s" :fill "freeze" :keyTimes "0 ; 1" :keyPoints "1 ; 0"}
        [:mpath {:xlink:href "#ear"}]]
       [:animateMotion {:xlink:href "#ear-end" :begin "revealhulk.end" :dur "1s" :fill "freeze" :keyTimes "0 ; 1" :keyPoints "1 ; 1"}
        [:mpath {:xlink:href "#ear"}]]

       ; revealeye + groweye
       [:animate {:id "revealeye" :xlink:href "#eye" :attribute-name "opacity" :from "0.0" :to "1.0" :begin "revealear.end" :dur "1s" :fill "freeze"}]
       [:animateTransform {:id "groweye" :xlink:href "#eye" :attribute-name "transform" :type "scale" :additive "sum" :from "0 0" :to "1 1" :begin "revealear.end" :dur "1s"}]

       ; hidehulk
       [:animate {:id "hidehulk" :xlink:href "#hulk" :attribute-name "stroke-dashoffset" :from "0" :to "1000" :begin "revealeye.end+0.1s" :dur "1s" :fill "freeze"}]

       ; hidehulk dots
       [:animateMotion {:xlink:href "#hulk-start" :begin "revealeye.end+0.1s" :dur "1s" :fill "freeze" :keyTimes "0 ; 1" :keyPoints "0 ; 0"}
        [:mpath {:xlink:href "#hulk"}]]
       [:animateMotion {:xlink:href "#hulk-end" :begin "revealeye.end+0.1s" :dur "1s" :fill "freeze" :keyTimes "0 ; 1" :keyPoints "1 ; 0"}
        [:mpath {:xlink:href "#hulk"}]]

       ; post-hidehulk
       [:set {:xlink:href "#hulk-start" :attribute-name "opacity" :to "0.0" :begin "hidehulk.end" :fill "freeze"}]
       [:set {:xlink:href "#hulk-end" :attribute-name "opacity" :to "0.0" :begin "hidehulk.end" :fill "freeze"}]

       ; hideear
       [:animate {:id "hideear" :xlink:href "#ear" :attribute-name "stroke-dashoffset" :from "0" :to "1000" :begin "hidehulk.end" :dur "1s" :fill "freeze"}]

       ; hideear dots
       [:animateMotion {:xlink:href "#ear-start" :begin "hidehulk.end" :dur "1s" :fill "freeze" :keyTimes "0 ; 1" :keyPoints "0 ; 0"}
        [:mpath {:xlink:href "#ear"}]]
       [:animateMotion {:xlink:href "#ear-end" :begin "hidehulk.end" :dur "1s" :fill "freeze" :keyTimes "0 ; 1" :keyPoints "1 ; 0"}
        [:mpath {:xlink:href "#ear"}]]

       ; post-hideear
       [:set {:xlink:href "#ear-start" :attribute-name "opacity" :to "0.0" :begin "hideear.end" :fill "freeze"}]
       [:set {:xlink:href "#ear-end" :attribute-name "opacity" :to "0.0" :begin "hideear.end" :fill "freeze"}]       

       ; hideeye + shrinkeye
       [:animate {:id "hideeye" :xlink:href "#eye" :attribute-name "opacity" :from "1.0" :to "0.0" :begin "hideear.end" :dur "1s" :fill "freeze"}]
       [:animateTransform {:id "shrinkeye" :xlink:href "#eye" :attribute-name "transform" :type "scale" :additive "sum" :from "1 1" :to "0 0" :begin "hideear.end" :dur "1s"}]
       ]))

(defn -main []
  (dali.io/render-svg (logo {:dot-radius 55
                             :line-width 64
                             :corner-arc-radius 160
                             :eye-offset 265})
                      "metamorphant-animation.svg"))
