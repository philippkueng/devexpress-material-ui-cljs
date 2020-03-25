(ns shadow-re-frame.devexpress
  (:require ["@material-ui/core/index.js" :as material-ui]
            ["@devexpress/dx-react-grid" :as dx-react-grid]
            ["@devexpress/dx-react-grid-export" :as dx-react-grid-export]
            ["@devexpress/dx-react-grid-material-ui" :as dx-react-grid-material-ui]
            [reagent.core :as r]))

;; mapping a couple of default material-ui components
(def button (r/adapt-react-class (aget material-ui "Button")))
(def input (r/adapt-react-class (aget material-ui "Input")))
(def paper (r/adapt-react-class (aget material-ui "Paper")))
(def switch (r/adapt-react-class (aget material-ui "Switch")))
(def typography (r/adapt-react-class (aget material-ui "Typography")))

;; mapping of devexpress components
(def data-type-provider (r/adapt-react-class (aget dx-react-grid "DataTypeProvider")))
(def integrated-paging (r/adapt-react-class (aget dx-react-grid "IntegratedPaging")))
(def integrated-sorting (r/adapt-react-class (aget dx-react-grid "IntegratedSorting")))
(def paging-state (r/adapt-react-class (aget dx-react-grid "PagingState")))
(def sorting-state (r/adapt-react-class (aget dx-react-grid "SortingState")))

;; mapping of devexpress export components
(def grid-exporter (r/adapt-react-class (aget dx-react-grid-export "GridExporter")))

;; mapping of devexpress material-ui components
(def export-panel (r/adapt-react-class (aget dx-react-grid-material-ui "ExportPanel")))
(def paging-panel (r/adapt-react-class (aget dx-react-grid-material-ui "PagingPanel")))
(def table (r/adapt-react-class (aget dx-react-grid-material-ui "Table")))
(def table-header-row (r/adapt-react-class (aget dx-react-grid-material-ui "TableHeaderRow")))
(def table-selection (r/adapt-react-class (aget dx-react-grid-material-ui "TableSelection")))
(def toolbar (r/adapt-react-class (aget dx-react-grid-material-ui "Toolbar")))
(def grid (r/adapt-react-class (aget dx-react-grid-material-ui "Grid")))
