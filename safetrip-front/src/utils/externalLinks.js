export const tourExternalLinks = {
  "Big Almaty Lake Day Tour": "",
  "Charyn Canyon Adventure": "",
};

export const placeExternalLinks = {
  "Navat": "https://2gis.kz/almaty/geo/70000001030309826",
  "Kok Tobe Viewpoint": "https://2gis.kz/almaty/geo/70000001017272265/76.976481,43.230913",
  "Tary Restaurant": "https://2gis.kz/almaty/geo/70000001063134361",
  "Sandyq": "https://2gis.kz/almaty/geo/70000001057699052",
  "Auyl Restaurant": "https://2gis.kz/almaty/geo/70000001067620756/77.059799,43.162334",
  "Kishlak": "https://2gis.kz/almaty/geo/9429940000796842/76.934847,43.240760",

  "Kok Tobe": "https://2gis.kz/almaty/geo/70000001017272265/76.976481,43.230913",
  "Fantasy World Almaty": "https://2gis.kz/almaty/geo/9429940000798123/76.918921,43.240118",
  "Arbat Street (Zhibek Zholy)": "https://2gis.kz/almaty/geo/9430098914574642/76.945254,43.262061",
  "Almaty Museum of Arts": "https://2gis.kz/almaty/geo/70000001089018631/76.949021,43.227179",
  "Medeu Skating Rink": "https://2gis.kz/almaty/geo/9429940000797844/77.059004,43.157544",
  "Shymbulak Ski Resort": "https://2gis.kz/almaty/geo/9429940001163496",
};

export const securityContactLinks = {
  telegram: "https://t.me/erslvv",
  instagram: "",
};

export function getTourExternalLink(title) {
  return tourExternalLinks[title] || "";
}

export function getPlaceExternalLink(title) {
  return placeExternalLinks[title] || "";
}
