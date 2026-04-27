import { Link } from "react-router-dom";
import Navbar from "../components/Navbar";
import almatyHero from "../assets/images/almaty.jpg";
import almatyCityImage from "../assets/images/almaty1.webp";
import bigAlmatyLakeImage from "../assets/images/bal.jpg";
import urtaImage from "../assets/images/urta.jpg";
import "./Home.css";

function Home() {
  const highlights = [
    {
      label: "Mountain escapes",
      title: "Big Almaty Lake",
      text: "Discover alpine views, fresh air, and guided routes near the city.",
      image: bigAlmatyLakeImage,
      link: "/tours",
      linkLabel: "View Tours",
    },
    {
      label: "Local food",
      title: "Taste Almaty",
      text: "Find trusted cafes, traditional dishes, and cozy city spots for tourists.",
      image: urtaImage,
      link: "/food",
      linkLabel: "Explore Food",
    },
    {
      label: "City moments",
      title: "Entertainment & culture",
      text: "Move from museums and viewpoints to evening activities and local events.",
      image: almatyCityImage,
      link: "/entertainment",
      linkLabel: "See More",
    },
  ];

  const quickSections = [
    {
      eyebrow: "Guided routes",
      title: "Tours",
      text: "Browse safe and verified trips around Almaty, then open a card to see full details.",
      link: "/tours",
    },
    {
      eyebrow: "Where to eat",
      title: "Food",
      text: "Show tourists trusted places to eat with atmosphere, pricing, and useful notes.",
      link: "/food",
    },
    {
      eyebrow: "What to do",
      title: "Entertainment",
      text: "Help visitors choose activities, city spots, and relaxing places after tours.",
      link: "/entertainment",
    },
    {
      eyebrow: "Stay prepared",
      title: "Security",
      text: "Collect important rules, emergency numbers, and basic local guidance in one place.",
      link: "/security",
    },
  ];

  return (
    <div className="home">
      <Navbar />

      <section className="hero" style={{ backgroundImage: `linear-gradient(120deg, rgba(9, 29, 44, 0.72), rgba(9, 29, 44, 0.18)), url(${almatyHero})` }}>
        <div className="hero__overlay" />
        <div className="hero__glow hero__glow--left" />
        <div className="hero__glow hero__glow--right" />

        <div className="hero__content">
          <p className="hero__subtitle">Almaty city guide for first-time visitors</p>

          <h1>
            Welcome to
            <br />
            Kazakhstan
          </h1>

          <p className="hero__text">
            Start in Almaty with verified tours, trusted food spots, entertainment ideas, and
            useful local information designed for tourists who want to feel confident from day one.
          </p>

          <div className="hero__stats">
            <div>
              <strong>City-first</strong>
              <span>Focused on Almaty routes</span>
            </div>
            <div>
              <strong>Verified</strong>
              <span>Safer travel picks</span>
            </div>
            <div>
              <strong>Easy flow</strong>
              <span>Explore, choose, open details</span>
            </div>
          </div>

          <div className="hero__buttons">
            <Link to="/tours" className="hero__btn hero__btn--primary">
              Explore Almaty
            </Link>

            <Link to="/login" className="hero__btn hero__btn--secondary">
              Login
            </Link>

            <Link to="/register" className="hero__btn hero__btn--accent">
              Register
            </Link>
          </div>
        </div>
      </section>

      <section className="intro">
        <div className="intro__copy">
          <p className="section-label">Discover the city with confidence</p>
          <h2>Almaty is your first stop, and this platform is your starting point.</h2>
          <p>
            The idea is simple: a tourist opens the website, learns what Kazakhstan and Almaty can
            offer, then moves into tours, food, entertainment, and safety information without
            getting lost.
          </p>
        </div>

        <div className="intro__visual">
          <div className="intro__card intro__card--large">
            <span>Home</span>
            <strong>Learn about the city first</strong>
          </div>
          <div className="intro__card">
            <span>Sections</span>
            <strong>Move through Tours, Food, Entertainment, Security</strong>
          </div>
          <div className="intro__card">
            <span>Cards</span>
            <strong>Open details, location, price, and description</strong>
          </div>
        </div>
      </section>

      <section className="featured">
        <div className="featured__header">
          <p className="section-label">What the user can explore</p>
          <h2>From mountain routes to food and city experiences.</h2>
          <p className="featured__text">
            The homepage should introduce the mood of Almaty and smoothly guide users into the
            sections where real cards and detailed pages live.
          </p>
        </div>

        <div className="cards">
          {highlights.map((item) => (
            <article className="card" key={item.title}>
              <img src={item.image} alt={item.title} />
              <div className="card__content">
                <p className="card__label">{item.label}</p>
                <h3>{item.title}</h3>
                <p>{item.text}</p>
                <Link to={item.link} className="card__link">
                  {item.linkLabel}
                </Link>
              </div>
            </article>
          ))}
        </div>
      </section>

      <section className="sections">
        <div className="sections__header">
          <p className="section-label">Main sections</p>
          <h2>Each section can become a working page connected to backend data.</h2>
        </div>

        <div className="sections__grid">
          {quickSections.map((section) => (
            <article className="section-card" key={section.title}>
              <p className="section-card__eyebrow">{section.eyebrow}</p>
              <h3>{section.title}</h3>
              <p>{section.text}</p>
              <Link to={section.link}>Open section</Link>
            </article>
          ))}
        </div>
      </section>
    </div>
  );
}

export default Home;
