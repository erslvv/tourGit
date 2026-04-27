import Navbar from "../components/Navbar";
import { securityContactLinks } from "../utils/externalLinks";
import "./Explore.css";

function Security() {
  const { telegram, instagram } = securityContactLinks;

  return (
    <main className="explore-page">
      <Navbar />

      <div className="explore-shell">
        <section className="explore-hero explore-hero--security">
          <span className="explore-hero__label">Security information</span>
          <h1>Important rules, numbers, and practical advice for tourists in Almaty.</h1>
          <p>
            This section gives the visitor one reliable place for emergency contacts and basic
            safety guidance, exactly like you wanted in the project idea.
          </p>

          <div className="explore-stats">
            <div>
              <strong>112</strong>
              <span>Emergency help</span>
            </div>
            <div>
              <strong>102</strong>
              <span>Police</span>
            </div>
            <div>
              <strong>103</strong>
              <span>Ambulance</span>
            </div>
          </div>
        </section>

        <div className="security-grid">
          <article className="security-card">
            <h3>Emergency numbers</h3>
            <ul>
              <li>112 for the main emergency line.</li>
              <li>102 for police support.</li>
              <li>103 for ambulance and medical emergency.</li>
              <li>101 for fire emergencies.</li>
            </ul>
          </article>

          <article className="security-card">
            <h3>Practical tourist tips</h3>
            <ul>
              <li>Keep your phone charged and save the address of your hotel.</li>
              <li>Use registered taxi apps or trusted transport options.</li>
              <li>Carry a copy or photo of your passport and visa documents.</li>
              <li>For mountain routes, check weather and avoid going late alone.</li>
            </ul>
          </article>

          <article className="security-card">
            <h3>Local behavior</h3>
            <p>
              Respect public spaces, local customs, and official instructions in parks, mountain
              areas, and public transport. When in doubt, ask hotel staff or official tourism help
              points.
            </p>
          </article>

          <article className="security-card">
            <h3>Why this page matters</h3>
            <p>
              The tourist should not only see beautiful cards. They should also quickly find rules,
              emergency numbers, and basic guidance when they need it.
            </p>
          </article>
        </div>

        <section className="security-contact">
          <article className="security-card">
            <h3>Contact Us</h3>
            <p>
              If you want to ask about tours, places, or general travel help, you can also contact
              our team through social media.
            </p>

            <div className="security-contact__actions">
              {telegram ? (
                <a className="explore-link" href={telegram} target="_blank" rel="noreferrer">
                  Telegram
                </a>
              ) : (
                <span className="explore-inline-note">Add Telegram link in `externalLinks.js`.</span>
              )}

              {instagram ? (
                <a
                  className="explore-link explore-link--external"
                  href={instagram}
                  target="_blank"
                  rel="noreferrer"
                >
                  Instagram
                </a>
              ) : (
                <span className="explore-inline-note">Add Instagram link in `externalLinks.js`.</span>
              )}
            </div>
          </article>
        </section>
      </div>
    </main>
  );
}

export default Security;
