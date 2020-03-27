
(ns shadow-re-frame.simple
  "Example of `re-frame-simple`, an alternate `re-frame` syntax for simple use cases."
  (:require
   [re-view.re-frame-simple :as db]
   [react :as react]
   [reagent.core :as reagent]
   [reagent.dom :as dom]
   [shadow-re-frame.welcome :as text]
   [shadow-re-frame.devexpress :as devexpress]
   ["file-saver/dist/FileSaver.min.js" :as file-saver]))

;;
;; For a complete introduction to `re-view.re-frame-simple`, see the readme:
;; https://github.com/braintripping/re-view/blob/master/re-frame-simple/README.md
;;


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; A COUNTER
;;
;; Example of...
;;
;; 1. Reading data using `db/get-in`
;;
;; 2. Writing data using `db/update-in!`
;;
;;


(defn counter
  "Given a counter id, render it as an interactive widget."
  [id]

  ;; NOTICE: `db/get-in`
  (let [total (db/get-in [:counters id])]

    ;; NOTICE: `db/update-in!`
    [:div.button {:on-mouse-down #(do
                                    (.preventDefault %)
                                    (db/update-in! [:counters id] inc))}
     total
     [:br]
     (if (pos? total)
       (take total (repeat id))
       [:span {:style {:color "#888"}} "click me!"])]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; NAMED UPDATES
;;
;; `defupdate` associates a keyword with an update function.
;;  this can be dispatched like any other re-frame handler.
;;

(db/defupdate :initialize
  "Initialize the `db` with the preselected emoji as counter IDs."
  [db]
  {:counter-ids (shuffle ["👹" "👺" "💩" "👻💀️"
                          "👽" "👾" "🤖" "🎃"
                          "😺" "👏" "🙏" "👅"
                          "👂" "👃" "👣" "👁"
                          "👀" "👨‍" "🚒" "👩‍✈️"
                          "👞" "👓" "☂️" "🎈"
                          "📜" "🏳️‍🌈" "🚣" "🏇"])})

(db/defupdate :new-counter
  "Create a new counter, using an ID from the pre-selected emoji."
  [db]
  (-> db
    (assoc-in [:counters (peek (:counter-ids db))] 0)
    (update :counter-ids pop)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Named queries
;;
;; use `defquery` to create named queries that read data using
;; `db/get` and `db/get-in`.
;;
;; `defquery` def's an ordinary Clojure function:
;;


(db/defquery counter-ids
  "Return the list of counters in the db, by id."
  []
  (-> (db/get :counters)
    (keys)))

;;
;; a component that uses the query will update when its data changes.
;;

(def divider [:div.font-large
              {:style {:margin "2rem 0 1rem"}}
              "〰️〰️〰️〰️〰️〰️〰️"])

(defn table-with-export []
  (let [exporter-ref (react/useRef)
        start-export (react/useCallback (fn [] (.exportGrid (.-current exporter-ref))) (clj->js [exporter-ref]))]
    (reagent/as-element
      [:div
       [devexpress/typography {:variant "h5"} "DevExpress: A normal table with paging, sorting, custom link formatting and export functionality"]
       [:br]
       [devexpress/paper
        (let [rows (for [item (range 12)]
                     {:product (str "Product " item)
                      :region (nth ["APAC" "US" "EUROPE"] (rand-int 3))
                      :amount (rand-int 100)
                      :saleDate "22.12.2019"
                      :customer "John"
                      :url (str "https://example.org/product" item)
                      :id item
                      :active (if (even? (rand-int 2))
                                "true"
                                "false")})
              columns [{:name "id" :title "ID"}
                       {:name "product" :title "Product"}
                       {:name "region" :title "Region"}
                       {:name "amount" :title "Amount"}
                       {:name "saleDate" :title "Sale Date"}
                       {:name "customer" :title "Customer"}
                       {:name "url" :title "URL"}
                       {:name "active" :title "Active"}]]
          [:div
           [devexpress/grid {:rows rows
                             :columns columns}
            ;; a custom data type provider seems to struggle with reagent's default of turning all the :for keys into :htmlFor
            ;; https://github.com/reagent-project/reagent/blob/ecbbc60d95e2fe6c51f679106bd0b0dc4a448101/src/reagent/impl/template.cljs#L37
            [devexpress/data-type-provider {"for" ["url"]
                                            :formatterComponent #(reagent/as-element [:a {:href (.-value %)} "Link"])}]
            [devexpress/data-type-provider {"for" ["active"]
                                            :formatterComponent #(reagent/as-element
                                                                   [devexpress/switch {:checked (if (= "true" (.-value %))
                                                                                                  true
                                                                                                  false)}])}]
            [devexpress/sorting-state {:defaultSorting [{:columnName "id"
                                                         :direction "asc"}]}]
            [devexpress/integrated-sorting]
            [devexpress/paging-state {:defaultCurrentPage 0
                                      :defaultPageSize 10}]
            [devexpress/integrated-paging]
            [devexpress/table]
            [devexpress/table-header-row {:showSortingControls true}]
            [devexpress/paging-panel {:pageSizes [2,5,10]}]
            [devexpress/toolbar]
            [devexpress/export-panel {:startExport start-export
                                      :messages {:exportAll "Customized export all the data message"}}]]
           [devexpress/grid-exporter {:ref exporter-ref
                                      :rows rows
                                      :columns columns
                                      :onSave (fn [^js workbook]
                                                (.then (.writeBuffer (.-xlsx workbook))
                                                  (fn [buffer]
                                                    (file-saver/saveAs (js/Blob. (clj->js [buffer]) (clj->js {:type "application/octet-stream"})) "Export.xlsx"))))}]])]])))

(defn root-view
  "Render the page"
  []
  [:div {:style {:background-color "#f5f5f5"
                 :height "100vh"
                 :width "100hw"
                 :padding "20px"}}
   ;; material-ui elements
   [devexpress/typography {:variant "h5"} "Material-UI components"]
   [devexpress/paper {:style {:padding "10px"}}
    [devexpress/button {:variant "contained"
                        :color "primary"}
     "Hello World"]]

   [:br]
   [:br]

   ;; devexpress table components
   ;; a normal table with paging and sorting
   [:div
    [devexpress/typography {:variant "h5"} "DevExpress: A normal table with paging and sorting"]
    [:br]
    [devexpress/paper
     [devexpress/grid {:rows (for [item (range 12)]
                               {:product (str "Product " item)
                                :region (nth ["APAC" "US" "EUROPE"] (rand-int 3))
                                :amount (rand-int 100)
                                :saleDate "22.12.2019"
                                :customer "John"
                                :url (str "https://example.org/product" item)
                                :id item})
                       :columns [{:name "id" :title "ID"}
                                 {:name "product" :title "Product"}
                                 {:name "region" :title "Region"}
                                 {:name "amount" :title "Amount"}
                                 {:name "saleDate" :title "Sale Date"}
                                 {:name "customer" :title "Customer"}]}
      [devexpress/sorting-state {:defaultSorting [{:columnName "id"
                                                   :direction "asc"}]}]
      [devexpress/integrated-sorting]
      [devexpress/paging-state {:defaultCurrentPage 0
                                :defaultPageSize 10}]
      [devexpress/integrated-paging]
      [devexpress/table]
      [devexpress/table-header-row {:showSortingControls true}]
      [devexpress/paging-panel {:pageSizes [2,5,10]}]]]]

   [:br]
   [:br]

   ;; a normal table with paging, sorting, links and export functionality
   [:> table-with-export]])



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;; Boilerplate code to get the page to render:

(defn ^:dev/after-load render []
  (dom/render [root-view]
    (js/document.getElementById "shadow-re-frame")))

(defn init []

  ;; initialize the db, create an example counter
  (db/dispatch [:initialize])
  (db/dispatch [:new-counter])
  ;; render to page
  (render))
