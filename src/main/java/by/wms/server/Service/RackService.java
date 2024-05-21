package by.wms.server.Service;

import by.wms.server.Repository.RackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RackService {

    private final RackRepository rackRepository;

}
