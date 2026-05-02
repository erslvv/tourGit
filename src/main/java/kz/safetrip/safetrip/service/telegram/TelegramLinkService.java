package kz.safetrip.safetrip.service.telegram;

import kz.safetrip.safetrip.model.dto.telegram.TelegramBindRequest;
import kz.safetrip.safetrip.model.dto.telegram.TelegramBindResponse;
import kz.safetrip.safetrip.model.dto.telegram.TelegramBindStartResponse;
import kz.safetrip.safetrip.model.dto.telegram.TelegramBindStatusResponse;

public interface TelegramLinkService {
    TelegramBindStartResponse startTelegramBinding();
    TelegramBindStatusResponse getTelegramBindingStatus();
    TelegramBindResponse bindTelegramAccount(TelegramBindRequest request);
}
