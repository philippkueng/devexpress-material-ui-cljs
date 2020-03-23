(ns shadow-re-frame.devexpress
  (:require ["@material-ui/core/index.js" :as material-ui]
            ["@devexpress/dx-react-grid" :as dx-react-grid]
            ["@devexpress/dx-react-grid-material-ui" :as dx-react-grid-material-ui]
            [reagent.core :as r]))

;; mapping a couple of default material-ui components
(def button (r/adapt-react-class (aget material-ui "Button")))
(def input (r/adapt-react-class (aget material-ui "Input")))
(def paper (r/adapt-react-class (aget material-ui "Paper")))
(def typography (r/adapt-react-class (aget material-ui "Typography")))



;; mapping of devexpress components
#_(def data-type-provider (r/adapt-react-class (aget dx-react-grid "DataTypeProvider")))
(def data-type-provider-unwrapped (aget dx-react-grid "DataTypeProvider"))
#_(defn data-type-provider [props]
    (apply r/create-element data-type-provider-unwrapped props))

(defn url-formatter [url]
  (str "url: " url))

#_(defn data-type-provider [props]
    (r/create-element data-type-provider-unwrapped
      (clj->js {:for ["url"]
                :formatterComponent url-formatter})))

#_(def data-type-provider-for
    (r/create-element data-type-provider-unwrapped
      (clj->js {:for ["url"]}))  )

#_(defn data-type-provider [props]
    [data-type-provider-for {:formatterComponent url-formatter}])

(def integrated-paging (r/adapt-react-class (aget dx-react-grid "IntegratedPaging")))
(def integrated-sorting (r/adapt-react-class (aget dx-react-grid "IntegratedSorting")))
(def paging-state (r/adapt-react-class (aget dx-react-grid "PagingState")))
(def sorting-state (r/adapt-react-class (aget dx-react-grid "SortingState")))

;; mapping of devexpress material-ui components
(def paging-panel (r/adapt-react-class (aget dx-react-grid-material-ui "PagingPanel")))
(def table (r/adapt-react-class (aget dx-react-grid-material-ui "Table")))
(def table-header-row (r/adapt-react-class (aget dx-react-grid-material-ui "TableHeaderRow")))
(def table-selection (r/adapt-react-class (aget dx-react-grid-material-ui "TableSelection")))
(def grid (r/adapt-react-class (aget dx-react-grid-material-ui "Grid")))

#_(defn url-formatter [url]
    [:a {:href url} url])

#_(def url-type-provider
    [data-type-provider {:formatterComponent url-formatter}])

#_(defn url-type-provider [args & children]
    (into [data-type-provider args]
      children))
