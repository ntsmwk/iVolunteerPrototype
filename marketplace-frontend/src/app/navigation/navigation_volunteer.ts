export const navigation_volunteer = [
  {
    'id': 'dashboard',
    'title': 'Übersicht',
    'type': 'item',
    'icon': 'dashboard',
    'url': '/main/dashboard'
  },



  {
    'id': 'achievements',
    'title': 'Erfolge',
    'type': 'group',
    'icon': 'achievements',
    'children': [
      {
        'id': 'achievements-feuerwehr',
        'title': 'Freiwillige Feuerwehr',
        'icon': 'engagements',
        'type': 'item',
        'url': 'main/achievements/fireBrigade'
      },
      {
        'id': 'achievements-musikverein',
        'title': 'Musikverein',
        'icon': 'engagements',
        'type': 'item',
        'url': '/main/achievements/music'
      },
      {
        'id': 'achievements-management-summary',
        'title': 'Zusammenfassung',
        'icon': 'engagements',
        'type': 'item',
        'url': 'main/achievements/summary'
      }
    ]
  },


  // {
  //   'id': 'achievements',
  //   'title': 'Erfolge',
  //   'type': 'collapsable',
  //   //'icon': 'achievements',
  //   //'url': '/main/achievements',
  //   'children': [
  //     {
  //       'id': 'achievements-feuerwehr',
  //       'title': 'Freiwillige Feuerwehr',
  //       'icon': 'engagements',
  //       'type': 'item',
  //       'url': '/main/achievements/fireBrigade'
  //     },
  //     {
  //       'id': 'achievements-musikverein',
  //       'title': 'Musikverein',
  //       'icon': 'engagements',
  //       'type': 'item',
  //       'url': '/main/achievements/music'
  //     }
  //   ]
  // },

  {
    'id': 'get-connected',
    'title': 'Community',
    'type': 'item',
    'icon': 'get-connected',
    'url': '/main/get-connected'
  }


];
