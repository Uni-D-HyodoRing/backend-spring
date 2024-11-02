package unid.hyodoring.service.fcm;

import java.util.Map;

public interface FCMService {

    Map sendMessage(Long member_id, String title, String body);
}
