(ns genlogo
  (:require [dali.io]))

(def logo
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

(defn -main []
  (dali.io/render-svg logo "metamorphant.svg"))
