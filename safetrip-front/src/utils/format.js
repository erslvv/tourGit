export function formatPrice(value) {
  if (value === null || value === undefined || value === "") {
    return "Price not specified";
  }

  const numeric = Number(value);

  if (Number.isNaN(numeric)) {
    return `${value} KZT`;
  }

  return `${new Intl.NumberFormat("en-US").format(numeric)} KZT`;
}

export function formatRating(value) {
  if (value === null || value === undefined || value === "") {
    return "New";
  }

  return Number(value).toFixed(1);
}

export function isFoodCategory(category = "") {
  const normalized = category.toLowerCase();
  return ["restaurant", "cafe", "coffee", "bakery", "food"].some((item) =>
    normalized.includes(item)
  );
}

export function getPlaceSubtitle(place) {
  return [place.category, place.city].filter(Boolean).join(" • ");
}
