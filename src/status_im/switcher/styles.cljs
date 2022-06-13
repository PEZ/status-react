(ns status-im.switcher.styles
  (:require [quo.theme :as theme]
            [quo2.foundations.colors :as colors]
            [status-im.switcher.constants :as constants]
            [status-im.switcher.animation :as animation]))

(def themes
  {:light {:bottom-tabs-bg-color           colors/neutral-80
           :bottom-tabs-on-scroll-bg-color colors/neutral-80-opa-80
           :bottom-tabs-non-selected-tab   colors/neutral-50
           :bottom-tabs-selected-tab       colors/white
           :switcher-close-button-bg-color colors/white}
   :dark  {:bottom-tabs-bg-color           colors/neutral-80
           :bottom-tabs-on-scroll-bg-color colors/neutral-80-opa-60
           :bottom-tabs-non-selected-tab   colors/neutral-40
           :bottom-tabs-selected-tab       colors/white
           :switcher-close-button-bg-color colors/white}}) ;; TODO - update close-button-bg-color-for-dark-theme

(defn get-color [key]
  (get-in themes [(theme/get-theme) key]))

;; Bottom Tabs
(defn bottom-tab-icon [tab-state]
  {:width  20
   :height 20
   :color  (get-color tab-state)})

(defn bottom-tabs []
  {:background-color (get-color :bottom-tabs-bg-color)
   :flex-direction   :row
   :flex             1
   :align-items      :center
   :justify-content  :space-around
   :height           (constants/bottom-tabs-height)
   :position         :absolute
   :bottom           -1
   :right            0
   :left             0})

;; Switcher

(defn switcher-button [opacity]
  {:width      constants/switcher-button-size
   :height     constants/switcher-button-size
   :opacity    opacity})

(defn merge-switcher-button-common-styles [style]
  (merge
   {:width           constants/switcher-button-size
    :height          constants/switcher-button-size
    :border-radius   constants/switcher-button-radius
    :position        :absolute
    :bottom          0
    :z-index         3
    :align-items     :center
    :alignSelf       :center ;; align-self is not working in animated style
    :justify-content :center}
   style))

(defn switcher-button-touchable [view-id]
  (merge-switcher-button-common-styles
   {:bottom (constants/switcher-bottom-position view-id)}))

(defn switcher-close-button [opacity]
  (merge-switcher-button-common-styles
   {:background-color (get-color :switcher-close-button-bg-color)
    :opacity          opacity}))

(defn switcher-screen [view-id radius]
  (let [bottom (- (constants/switcher-center-position view-id) radius)
        size   (* 2 radius)]
    (merge-switcher-button-common-styles
     {:background-color colors/switcher-background-opa-80
      :bottom           bottom
      :border-radius    1000
      :width            size
      :height           size
      :overflow         :hidden})))

(defn switcher-screen-container [view-id radius]
  (let [radius                 radius
        bottom                 (- radius (constants/switcher-center-position view-id))
        {:keys [width height]} (constants/dimensions)]
    {:position         :absolute
     :align-self       :center
     :bottom           bottom
     :width            width
     :height           (- height 25)
     :align-items      :center}))

(defn switcher-switch-screen []
  {:margin-top  40
   :align-items :center})
