(ns quo.reanimated
  (:require ["react-native" :as rn]
            [reagent.core :as reagent]
            ["react-native-reanimated" :default reanimated :refer (useSharedValue useAnimatedStyle withTiming Easing)]))


;; Animated Components
(def create-animated-component (comp reagent/adapt-react-class (.-createAnimatedComponent reanimated)))

(def view (reagent/adapt-react-class (.-View reanimated)))
(def touchable-opacity (create-animated-component (.-TouchableOpacity ^js rn)))

;; Hooks 
(def use-shared-value useSharedValue)
(def use-animated-style useAnimatedStyle)

;; Animations
(def with-timing withTiming)

;; Easings
(def bezier (.-bezier ^js Easing))

(def easings {:easing1 (bezier 0.25 0.1 0.25 1) ;; TODO(parvesh) - rename easing functions, (design team input)
              :easing2 (bezier 0 0.3 0.6 0.9)})

;; Worklets
(def worklet-factory (js/require "../js/worklet_factory.js"))


;; apply animations to style
(defn apply-animations-to-style [animations style]
  (use-animated-style
   (.applyAnimationsToStyle ^js worklet-factory (clj->js animations) (clj->js style))))

;; Getter, Setters
(defn get-shared-value [anim]
  (.-value anim))

(defn set-shared-value [anim val]
  (set! (.-value anim) val))

;; Animators
(defn animate-shared-value-with-timing [anim val duration easing]
  (set-shared-value anim (with-timing val (clj->js {:duration duration
                                                    :easing   (get easings easing)}))))
