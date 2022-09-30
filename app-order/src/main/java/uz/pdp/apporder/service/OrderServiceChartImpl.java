package uz.pdp.apporder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.pdp.apporder.entity.Order;
import uz.pdp.apporder.entity.enums.OrderStatusEnum;
import uz.pdp.apporder.entity.enums.PaymentType;
import uz.pdp.apporder.exceptions.RestException;
import uz.pdp.apporder.payload.ApiResult;
import uz.pdp.apporder.payload.OrderChartOrderDTO;
import uz.pdp.apporder.payload.OrderChartPaymentDTO;
import uz.pdp.apporder.payload.OrderStatisticsChartDTO;
import uz.pdp.apporder.repository.BranchRepository;
import uz.pdp.apporder.repository.OrderProductRepository;
import uz.pdp.apporder.repository.OrderRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;


@Service
@RequiredArgsConstructor
public class OrderServiceChartImpl implements OrderServiceChart {
    private final BranchRepository branchRepository;

    private final OrderRepository orderRepository;

    private final OrderProductRepository orderProductRepository;

    /**
     * Tegilmasin
     * <p>Show Statistics for admin with chart diagram</p>
     *
     */
    public ApiResult<OrderStatisticsChartDTO> getStatisticsOrder(OrderChartOrderDTO orderChartOrderDTO) {

        checkOrderChartDTO(orderChartOrderDTO.getBranchId(),
                orderChartOrderDTO.getTillDate(),
                orderChartOrderDTO.getFromDate(),
                orderChartOrderDTO.getOrderStatusEnum());

        Map<?, Integer> map = countingOrderByStatusAndDate(orderChartOrderDTO);

        OrderStatisticsChartDTO orderStatisticsDTO = new OrderStatisticsChartDTO(
                map, orderChartOrderDTO.getBranchId(),
                orderChartOrderDTO.getFromDate(),
                orderChartOrderDTO.getTillDate(),
                orderChartOrderDTO.getOrderStatusEnum()
        );

        return ApiResult.successResponse(orderStatisticsDTO);

    }


    /**
     * Bu getStatisticsForChart method qismi
     *
     */
    private Map<?,Integer> countingOrderByStatusAndDate(OrderChartOrderDTO orderChartOrderDTO) {

        LocalDate fromDate = orderChartOrderDTO.getFromDate();
        LocalDate tillDate = orderChartOrderDTO.getTillDate();

        List<Order> all = orderRepository.findAllByStatusEnumEquals(orderChartOrderDTO.getOrderStatusEnum());

        if (fromDate.plusDays(30).isBefore(tillDate)) {
            Map<Month,Integer> monthMap = new TreeMap<>();
            countingByMonth(orderChartOrderDTO,fromDate,tillDate, monthMap,all);
            return monthMap;
        }
        else {
            Map<LocalDate,Integer> dayMap = new TreeMap<>();
            while (!fromDate.isAfter(tillDate)) {
                dayMap.put(fromDate,getCountByDay(orderChartOrderDTO, fromDate, all));
                fromDate = fromDate.plusDays(1);
            }
            return dayMap;
        }
    }

    /**
     * count order by day
     */
    private int getCountByDay(OrderChartOrderDTO orderChartOrderDTO,
                              LocalDate fromDate, List<Order> all) {
        int count = 0;
        boolean rejected = orderChartOrderDTO.getOrderStatusEnum().equals(OrderStatusEnum.REJECTED);
        for (Order order : all)
        {
            if (rejected && Objects.equals(order.getCancelledAt().toLocalDate(), fromDate)) {
                if (Objects.isNull(orderChartOrderDTO.getBranchId()))
                    count++;
                else if (Objects.equals(order.getBranch().getId(), orderChartOrderDTO.getBranchId()))
                    count++;
            } else if (Objects.equals(order.getCancelledAt().toLocalDate(), fromDate))
                count++;
        }
        return count;
    }

    /**
     * depend on counting
     */
    private void countingByMonth(OrderChartOrderDTO orderChartOrderDTO,
                                 LocalDate fromDate,
                                 LocalDate tillDate,
                                 Map<Month, Integer> monthMap,
                                 List<Order> all){

        Set<Month> set = new HashSet<>();
        int count = 0;
        while (fromDate.isBefore(tillDate)){
            set.add(fromDate.getMonth());

            if (!set.contains(fromDate.getMonth())){
                monthMap.put(fromDate.minusDays(1).getMonth(),count);
                count = 0;
            }

            count += getCountByDay(orderChartOrderDTO, fromDate, all);
            fromDate = fromDate.plusDays(1);

        }
    }


    /**
     *
     * Tegilmasin
     * Bu getStatisticsForChart methodi qismi
     *
     */
    private void checkOrderChartDTO(Integer branchId,
                                    LocalDate tillDate,
                                    LocalDate fromDate,
                                    OrderStatusEnum orderStatusEnum) {

        if (!Objects.isNull(branchId) && !branchRepository.existsById(branchId))
            throw RestException.restThrow("Bunday filial mavjud emas!", HttpStatus.NOT_FOUND);

        if (tillDate.isBefore(fromDate))
            throw RestException.restThrow("Vaqtlar no'togri berilgan!", HttpStatus.BAD_REQUEST);

        if (tillDate.isAfter(LocalDate.now()))
            throw RestException.restThrow("Kelajakda nima bo'lishini xudo biladi!", HttpStatus.BAD_REQUEST);

        if (!Objects.isNull(orderStatusEnum)&&!orderStatusEnum.equals(OrderStatusEnum.FINISHED)
                && !orderStatusEnum.equals(OrderStatusEnum.REJECTED))
            throw RestException.restThrow("Faqat Rejected va Finished statuslari uchungina statistica mavjud!"
                    , HttpStatus.NOT_FOUND);
    }

    /**
     * show payment statistics by payment
     */
    @Override
    public ApiResult<OrderStatisticsChartDTO> getStatisticsPayment(OrderChartPaymentDTO orderChartPaymentDTO) {

        OrderStatisticsChartDTO statisticsChartDTO = new OrderStatisticsChartDTO();

        checkOrderChartDTO(orderChartPaymentDTO.getBranchId(),
                orderChartPaymentDTO.getTillDate(),
                orderChartPaymentDTO.getFromDate(),
                null);

        sumPayment(orderChartPaymentDTO,statisticsChartDTO);

        return ApiResult.successResponse(statisticsChartDTO);
    }

    private void sumPayment(OrderChartPaymentDTO orderChartPaymentDTO,
                            OrderStatisticsChartDTO statisticsChartDTO){

        LocalDate fromDate = orderChartPaymentDTO.getFromDate();
        LocalDate tillDate = orderChartPaymentDTO.getTillDate();

        Double paymentPayme = 0D;
        Double paymentClick= 0D;
        Double paymentChash = 0D;
        Double paymentTerminal = 0D;
        Double totalPayment = 0D;

        while (fromDate.isBefore(tillDate)){

            List<Order> allByClosedAt = orderRepository.findAllByClosedAt(
                    LocalDateTime.from(fromDate));

            for (Order order : allByClosedAt) {
                if (Objects.equals(order.getPaymentType(), PaymentType.CASH)){
                    paymentChash+=1;
                }
            }

            fromDate = fromDate.plusDays(1);

        }
    }
}
