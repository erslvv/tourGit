import { securityContactLinks } from "../utils/externalLinks";
import "./Footer.css";

function Footer() {
  const { telegram, instagram } = securityContactLinks;

  return (
    <footer className="site-footer">
      <div className="site-footer__inner">
        <div className="site-footer__copy">
          <p className="site-footer__label">Contact SafeTrip</p>
          <h3>Have questions about tours, bookings, or city guidance?</h3>
          <p>
            If you need help or want to ask something before your trip, contact us through the
            channels below and we will get back to you.
          </p>
        </div>

        <div className="site-footer__actions">
          {telegram ? (
            <a href={telegram} target="_blank" rel="noreferrer" className="site-footer__link">
              Telegram
            </a>
          ) : (
            <span className="site-footer__note">Add Telegram link later</span>
          )}

          {instagram ? (
            <a
              href={instagram}
              target="_blank"
              rel="noreferrer"
              className="site-footer__link site-footer__link--secondary"
            >
              Instagram
            </a>
          ) : (
            <span className="site-footer__note">Add Instagram link later</span>
          )}

          <a href="mailto:safetrip@gmail.com" className="site-footer__link site-footer__link--email">
            safetrip@gmail.com
          </a>
        </div>
      </div>
    </footer>
  );
}

export default Footer;
