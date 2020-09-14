export const navigation_tenantAdmin = [
  {
    id: "welcome",
    title: "Übersicht",
    type: "item",
    icon: "door-open",
    url: "/main/dashboard"
  },
  {
    id: "digi",
    title: "Digitalisieren",
    type: "group"
  },
  {
    id: "tasks",
    title: "Einträge erfassen",
    type: "item",
    icon: "pencil",
    url: "/main/tasks/all"
  },
  {
    id: "tasks",
    title: "Einträge importieren",
    type: "item",
    icon: "import",
    url: "/main/import"
  },
  {
    id: "inbox",
    title: "Einträge bestätigen",
    type: "item",
    icon: "unlock",
    url: "/main/helpseeker/asset-inbox"
  },
  {
    id: "config",
    title: "Konfigurieren",
    type: "group"
  },
  {
    id: "configurator",
    title: "Einträge konfigurieren",
    type: "item",
    icon: "cogs",
    url: "/main/class-configurator"
  },
  {
    id: "matching-configurator",
    title: "Matching konfigurieren",
    type: "item",
    icon: "less-than-equal-solid",
    url: "/main/matching-configurator/"
  },
  {
    id: "rule-configurator",
    title: "Einträge ableiten",
    type: "item",
    icon: "share-square",
    url: "/main/rules/all"
  },
  {
    id: "property-builder",
    title: "Properties konfigurieren",
    type: "item",
    icon: "hammer-solid",
    url: "/main/properties/all/"
  }
];
