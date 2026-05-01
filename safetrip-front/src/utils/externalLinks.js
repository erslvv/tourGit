export const tourExternalLinks = {
  
};

export const placeExternalLinks = {
  
};

export const securityContactLinks = {
  telegram: "https://t.me/erslvv",
  instagram: "https://www.instagram.com/safetripkz?igsh=aHF3OWcxYTBoN3d6&utm_source=qr",
};

export function getTourExternalLink(title) {
  return title ? tourExternalLinks[title] || "" : "";
}

export function getPlaceExternalLink(title) {
  return title ? placeExternalLinks[title] || "" : "";
}

