package com.luccas.schedulerpii.task;

import com.luccas.schedulerpii.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskUpdateEncryption {

        private final UserService userService;

        public TaskUpdateEncryption(final UserService userService) {
                this.userService = userService;
        }

        @Scheduled(cron = "2 * * * * ?" )
        public void updateEncryptionByVersion() {
            this.userService.updateEncryptionByVersion();
        }

        @Scheduled(cron = "2 * * * * ?" )
        public void updateEncryptionByTimestamp() {
                this.userService.updateEncryptionByTimestamp();
        }
}
