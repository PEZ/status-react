(ns status-im.ui.screens.chat.styles.message.datemark
  (:require [quo.design-system.colors :as colors]))

(def datemark-mobile
  {:flex        1
   :justify-content :center
   :margin-vertical 16
   :padding-left 65})

(def divider
  {:flex        1
   :width           "100%"
   :height          1
   :padding-left 53
   :background-color (:ui-01 @colors/theme)
   :margin-top 5})

(defn datemark-text []
  {:color colors/gray-darker
   :font-size 14
   :line-height 16
   :font-weight "500"})
