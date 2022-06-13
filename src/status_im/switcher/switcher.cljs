(ns status-im.switcher.switcher
  (:require [quo.react-native :as rn]
            [reagent.core :as reagent]
            [quo.reanimated :as reanimated]
            [status-im.switcher.styles :as styles]
            [status-im.ui.components.animation :as anim]
            [status-im.switcher.animation :as animation]
            [status-im.ui.components.icons.icons :as icons]
            [status-im.react-native.resources :as resources]
            [status-im.switcher.switcher-container :as switcher-container]))

(defn toggle-switcher-screen [switcher-opened? view-id anim-values]
  (swap! switcher-opened? not)
  (animation/animate @switcher-opened? view-id anim-values))

(defn switcher-button [switcher-opened? view-id anim-values]
  [:f>
   (fn []
     (let [touchable-scale          (reanimated/use-shared-value 1)
           close-button-opacity     (reanimated/use-shared-value 0)
           switcher-button-opacity  (reanimated/use-shared-value 1)
           touchable-original-style (styles/switcher-button-touchable view-id)
           touchable-animated-style (reanimated/apply-animations-to-style
                                     {:transform [{:scale touchable-scale}]}
                                     touchable-original-style)]
       [reanimated/touchable-opacity {:active-opacity 1
                                      :on-press-in    #(reanimated/animate-shared-value-with-timing
                                                        touchable-scale 0.9 300 :easing1)
                                      :on-press-out   #(reanimated/animate-shared-value-with-timing
                                                        touchable-scale 1 300 :easing2)
                                      :style          touchable-animated-style}
        [rn/animated-view {:style (styles/switcher-close-button
                                   (:switcher-close-button-opacity anim-values))}
         [icons/icon :main-icons/close {:color :black}]]
        [rn/animated-image-view {:source (resources/get-image :status-logo)
                                 :style  (styles/switcher-button
                                          (:switcher-button-opacity anim-values))}]]))])

(defn switcher-screen [switcher-opened? view-id anim-values]
  [rn/view {:style          (styles/switcher-screen
                             view-id @(:switcher-screen-radius anim-values))
            :pointer-events (if switcher-opened? :auto :none)}
   [switcher-container/container
    view-id @(:switcher-screen-radius anim-values)
    #(toggle-switcher-screen switcher-opened? view-id anim-values)]])

(defn switcher [view-id]
  (let [switcher-opened? (reagent/atom false)
        anim-values      {:switcher-button-opacity                  (anim/create-value 1)
                          :switcher-close-button-opacity            (anim/create-value 0)
                          :switcher-screen-radius                   (reagent/atom 0)}]
    [:<>
     [switcher-screen switcher-opened? view-id anim-values]
     [switcher-button switcher-opened? view-id anim-values]]))
