(ns genlogo
  (:require [dali.io]))

(def first-sloppy-logo
  [:dali/page {:width "100%" :height "100%" :view-box "-20 -20 1040 1040"}
   [:defs
    [:circle {:id "bubbel" :cx 0 :cy 0 :r 50 :fill "black"}]
    [:marker {:id "bm" :view-box "0 0 100 100" :ref-x 50 :ref-y 50 :marker-units "strokeWidth" :marker-width 1.667 :marker-height 1.667}
     [:use {:x 50 :y 50 :xlink:href "#bubbel"}]]]
   [:path {:stroke "black" :path-length 1000 :stroke-width 60 :stroke-linejoin "round" :fill "none" :marker-start "url(#bm)" :marker-end "url(#bm)"}
    :M [390 740]
    :L [390 950]
    :L [250 950]
    :A [200 200] 0 false true [50 750]
    :L [50 250]
    :A [200 200] 0 false true [250 50]
    :L [750 50]
    :A [200 200] 0 false true [950 250]
    :L [950 750]
    :A [200 200] 0 false true [750 950]]
   [:path {:stroke "black" :path-length 1000 :stroke-width 60 :stroke-linejoin "round" :fill "none" :marker-start "url(#bm)" :marker-end "url(#bm)"}
    :M [390 180]
    :L [390 300]
    :A [200 200] 0 false false [590 500]
    :L [710 500]
    :L [710 750]
    :A [200 200] 0 false true [510 950]]
   [:use {:x 730 :y 270 :xlink:href "#bubbel"}]])

(def logo
  (let [dot-radius 50
        line-width 60
        eye {:x 730 :y 270}
        inner-outline-dot {:x 390 :y 740}
        corner-arc-radius 200
        inner-ear-dot {:x (:x inner-outline-dot) :y 180}
        bottom-middle-dot {:x 510}
        line-padding 50
        canvas {:width 1000 :height 1000}
        dot-diameter (* 2 dot-radius)]
      [:dali/page {:width "100%" :height "100%" :view-box "-20 -20 1040 1040"}
       [:defs
        [:circle {:id "bubbel" :cx 0 :cy 0 :r dot-radius :fill "black"}]
        [:marker {:id "bm" :view-box (str "0 0 " dot-diameter " " dot-diameter) :ref-x dot-radius :ref-y dot-radius :marker-units "userSpaceOnUse" :marker-width dot-diameter :marker-height dot-diameter}
         [:use {:x dot-radius :y dot-radius :xlink:href "#bubbel"}]]]
       [:path {:stroke "black" :path-length 1000 :stroke-width line-width :stroke-linejoin "round" :fill "none" :marker-start "url(#bm)" :marker-end "url(#bm)"}
        :M [(:x inner-outline-dot) (:y inner-outline-dot)]
        :L [(:x inner-outline-dot) (- (:height canvas) line-padding)]
        :L [(+ corner-arc-radius line-padding) (- (:height canvas) line-padding)]
        :A [corner-arc-radius corner-arc-radius] 0 false true [line-padding (- (:height canvas) corner-arc-radius line-padding)]
        :L [line-padding (+ corner-arc-radius line-padding)]
        :A [corner-arc-radius corner-arc-radius] 0 false true [(+ corner-arc-radius line-padding) line-padding]
        :L [(- (:width canvas) corner-arc-radius line-padding) line-padding]
        :A [corner-arc-radius corner-arc-radius] 0 false true [(- (:width canvas) line-padding) (+ corner-arc-radius line-padding)]
        :L [(- (:width canvas) line-padding) (- (:height canvas) corner-arc-radius line-padding)]
        :A [corner-arc-radius corner-arc-radius] 0 false true [(- (:width canvas) corner-arc-radius line-padding) (- (:height canvas) line-padding)]]
       [:path {:stroke "black" :path-length 1000 :stroke-width line-width :stroke-linejoin "round" :fill "none" :marker-start "url(#bm)" :marker-end "url(#bm)"}
        :M [(:x inner-ear-dot) (:y inner-ear-dot)]
        :L [(:x inner-ear-dot) (- (/ (:height canvas) 2) corner-arc-radius)]
        :A [corner-arc-radius corner-arc-radius] 0 false false [(+ (:x inner-ear-dot) corner-arc-radius) (/ (:height canvas) 2)]
        :L [(+ (:x bottom-middle-dot) corner-arc-radius) (/ (:height canvas) 2)]
        :L [(+ (:x bottom-middle-dot) corner-arc-radius) (- (:height canvas) corner-arc-radius line-padding)]
        :A [corner-arc-radius corner-arc-radius] 0 false true [(:x bottom-middle-dot) (- (:height canvas) line-padding)]]
       [:use {:x (:x eye) :y (:y eye) :xlink:href "#bubbel"}]]))

(defn -main []
  (dali.io/render-svg logo "metamorphant.svg"))
